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
public class SequenceFlowTest {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;


    @Test
    public void testStart(){
        // 启动流程
        ProcessInstance process = runtimeService.startProcessInstanceByKey("process");
        System.out.println("启动成功：流程实例ID："+process.getId());
    }

    @Test
    public void testCompleteTask(){
        // 完成 提交请求 任务
        Task task = null;
        task = taskService.createTaskQuery().processInstanceId("2501").singleResult();
        System.out.println("任务id："+task.getId());
        System.out.println("任务名称："+task.getName());
        System.out.println("任务办理人："+task.getAssignee());
        taskService.complete(task.getId());

        // 完成任务并设置流程变量 决定流程走向
        task = taskService.createTaskQuery().processInstanceId("2501").singleResult();
        System.out.println("任务id："+task.getId());
        System.out.println("任务名称："+task.getName());
        System.out.println("任务办理人："+task.getAssignee());
        Map<String,Object> variables = new HashMap<>();
        variables.put("outcome","重要");
        taskService.complete(task.getId(),variables);

        // 完成之后的任务
        task = taskService.createTaskQuery().processInstanceId("2501").singleResult();
        System.out.println("任务id："+task.getId());
        System.out.println("任务名称："+task.getName());
        System.out.println("任务办理人："+task.getAssignee());
        taskService.complete(task.getId());
    }
}
