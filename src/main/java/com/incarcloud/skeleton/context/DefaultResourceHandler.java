package com.incarcloud.skeleton.context;

import com.incarcloud.skeleton.anno.ICSComponent;
import com.incarcloud.skeleton.anno.ICSConditionalOnMissingBean;
import com.incarcloud.skeleton.exception.BaseRuntimeException;
import com.incarcloud.skeleton.request.RequestData;
import com.incarcloud.skeleton.util.FileUtil;
import com.incarcloud.skeleton.config.Config;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * 静态资源处理器
 */
@ICSComponent("resourceHandler")
@ICSConditionalOnMissingBean(name="resourceHandler")
public class DefaultResourceHandler implements ResourceHandler,Initializable {

    private Context context;

    public Context getContext() {
        return context;
    }

    /**
     * 定义文件后缀和响应类型映射
     */
    private final static Map<String,String> REQUEST_SUFFIX_TO_RESPONSE_TYPE=new HashMap<String,String>(){{
        put("jpeg","image/jpeg");
        put("jpg","image/jpg");
        put("png","image/png");
        put("html","text/html");
        put("js","text/javascript");
        put("css","text/css");
    }};

    @Override
    public void handleResource(RequestData requestData) {
        HttpServletResponse response=requestData.getResponse();
        Config config= context.getConfig();
        //1、获取子路径
        String subPath=requestData.getSubPath();
        //1.1、根据子路径和配置的静态文件请求路径、额外的类路径静态文件存放路径、静态文件存放路径来拼装正确的静态文件相对地址
        String subFilePath=subPath.substring(config.getRequestStaticMappingPre().length());
        String[] extFileStaticMappingPres= config.getExtFileStaticMappingPres();
        LinkedHashSet<String> fileStaticMappingPreSet;
        if(extFileStaticMappingPres==null||extFileStaticMappingPres.length==0){
            fileStaticMappingPreSet=new LinkedHashSet<>();
        }else{
            fileStaticMappingPreSet= Arrays.stream(extFileStaticMappingPres).collect(Collectors.toCollection(LinkedHashSet::new));
        }
        fileStaticMappingPreSet.add(config.getFileStaticMappingPre());
        //2、循环所有的类路径静态文件存放路径,依次检查每一个资源
        List<String> filePathList=new ArrayList<>();
        for (String fileStaticMappingPre : fileStaticMappingPreSet) {
            String filePath=fileStaticMappingPre+subFilePath;
            filePath=filePath.substring(1);
            filePathList.add(filePath);
            //2.1、读取静态文件内容
            try(InputStream is=ClassLoader.getSystemResourceAsStream(filePath)){
                //2.2、如果静态文件存在,则返回文件,并设置资源标记为true
                if(is!=null){
                    response.setCharacterEncoding(config.getEncoding());
                    response.setContentLength(is.available());
                    setResponseType(subPath,response);
                    FileUtil.write(is,response.getOutputStream());
                    return;
                }
            } catch (IOException e) {
                throw BaseRuntimeException.getException(e);
            }
        }
        //3、如果有循环完了都没有资源,则抛出异常
        String msg="ResourceHandler subPath["+subPath+"] mapping classPath["+filePathList.stream().reduce((e1,e2)->e1+","+e2).orElse("")+"] not exists";
        config.getLogger().log(Level.SEVERE,msg);
        throw BaseRuntimeException.getException(msg);
    }

    /**
     * 根据访问文件后缀设置response的响应类型
     * @param subPath
     * @param response
     */
    private void setResponseType(String subPath,HttpServletResponse response){
        int index= subPath.lastIndexOf('.');
        if(index!=-1){
            if(index<subPath.length()-1){
                String suffix= subPath.substring(index+1).toLowerCase();
                String responseType= REQUEST_SUFFIX_TO_RESPONSE_TYPE.get(suffix);
                if(responseType!=null){
                    response.setContentType(responseType);
                }
            }
        }
    }

    @Override
    public void init(Context context) {
        this.context=context;
    }
}
