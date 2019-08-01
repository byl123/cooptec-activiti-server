package cn.activitiserver;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessDefTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;


    @Test
    public void testQueryProcessDeployment(){
        // 查询 部署流程
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId("7").singleResult();
        System.out.println("部署流程ID："+deployment.getId());
        System.out.println("部署流程名称："+deployment.getName());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("部署流程时间："+format.format(deployment.getDeploymentTime()));
    }

    @Test
    public void testQueryProcDef(){
        // 查询 流程定义
        ProcessDefinitionQuery procDef = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> processDefinitions = procDef.listPage(0, 10);
        for (ProcessDefinition processDefinition:processDefinitions){
            System.out.println("流程定义ID："+processDefinition.getId());
            System.out.println("流程定义Key："+processDefinition.getKey());
            System.out.println("流程定义Name："+processDefinition.getName());
            System.out.println("流程定义bpmn Name："+processDefinition.getResourceName());
            System.out.println("流程部署ID："+processDefinition.getDeploymentId());
            System.out.println("流程定义版本："+processDefinition.getVersion());
            System.out.println("###############################################");
        }
    }

    @Test
    public void testImage(){
        InputStream in = repositoryService.getProcessDiagram("process:1:7504");
        File file = new File("C:\\Users\\Administrator\\Desktop\\test.png");
        try {
            BufferedInputStream input = new BufferedInputStream(in);
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buf = new byte[1024];
            int len = 0;
            while((len = input.read(buf))!=-1){
                out.write(buf,0,len);
            }
            out.flush();
            out.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStartProcess(){
        // 启动 流程
        // 参数一： 流程定义的Key
        // 参数二： businessKey 用于绑定指定用户提交的流程
        // 参数三： 流程变量，每次流程携带的参数，从来做判断，是否进行下一阶段或其他
        // ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables);
        ProcessInstance process = runtimeService.startProcessInstanceByKey("process");
        System.out.println("启动流程成功："+process.getId());
    }

    @Test
    public void testTask(){
        // 查询 任务
        Task task = taskService.createTaskQuery().taskAssignee("张三").singleResult();

        // 完成任务
        taskService.complete(task.getId());
    }

    @Test
    public void testQueryProcessInstance(){
        // 查询当前的流程实例 act_ru_execution
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
    }
}
