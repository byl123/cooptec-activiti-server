package cn.activitiserver;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExclusiveGateWayTest {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;


    @Test
    public void testStart(){
        // 启动流程
        ProcessInstance process = runtimeService.startProcessInstanceByKey("gateway");
        System.out.println("启动成功：流程实例ID："+process.getId());
    }

    @Test
    public void testCompleteTask(){
        // 完成 报销申请 任务
        Task task = null;
        /*task = taskService.createTaskQuery().processInstanceId("25001").singleResult();
        System.out.println("任务id："+task.getId());
        System.out.println("任务名称："+task.getName());
        System.out.println("任务办理人："+task.getAssignee());
        taskService.complete(task.getId());*/

        // 设置网关条件 决定流程走向
        task = taskService.createTaskQuery().processInstanceId("25001").singleResult();
        System.out.println("任务id："+task.getId());
        System.out.println("任务名称："+task.getName());
        System.out.println("任务办理人："+task.getAssignee());
        Map<String,Object> variables = new HashMap<>();
        variables.put("money",1000);
        taskService.complete(task.getId(),variables);

        // 完成之后的任务
       /* task = taskService.createTaskQuery().processInstanceId("15001").singleResult();
        System.out.println("任务id："+task.getId());
        System.out.println("任务名称："+task.getName());
        System.out.println("任务办理人："+task.getAssignee());
        taskService.complete(task.getId());*/
    }
}
