package cn.activitiserver;

import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiServerApplicationTests {

    // 存储服务
    @Autowired
    private RepositoryService repositoryService;
    // 运行时服务
    @Autowired
    private RuntimeService runtimeService;
    // 任务服务
    @Autowired
    private TaskService taskService;

    @Test
    public void testStart(){
        // 创建部署任务
        //repositoryService.createDeployment().addClasspathResource("processes/first.bpmn").deploy();

        // 启动流程任务
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("process");
        System.out.println("启动任务的id："+pi.getId());

        // 普通用户完成节点任务
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //查的act_hi_procinst表的id
        System.out.println("流程实例ID="+task.getProcessInstanceId());
        //查的act_hi_taskinst表的id
        System.out.println("任务ID="+task.getId());
        //查的act_hi_taskinst表的Assignee_
        System.out.println("任务负责人名称="+task.getAssignee());
        //查的act_hi_taskinst表的NAME_
        System.out.println("任务名称="+task.getName());
        // 完成任务
        taskService.complete(task.getId());

        // 领导完成节点任务
        task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //查的act_hi_procinst表的id
        System.out.println("流程实例ID="+task.getProcessInstanceId());
        //查的act_hi_taskinst表的id
        System.out.println("任务ID="+task.getId());
        //查的act_hi_taskinst表的Assignee_
        System.out.println("任务负责人名称="+task.getAssignee());
        //查的act_hi_taskinst表的NAME_
        System.out.println("任务名称="+task.getName());
        // 完成任务
        taskService.complete(task.getId());

        // 普通用户完成节点任务
        task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        System.out.println("流程结束："+task);
    }

    @Test
    public void startProcess(){
        // 启动任务
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("process");
        System.out.println("启动任务的id："+pi.getId());
        System.out.println(pi.getProcessDefinitionId());
    }

    @Test
    public void findTask(){
        // 查询任务
        List<Task> list = taskService.createTaskQuery().taskAssignee("张三").list();
        for(Task task:list){
            System.out.println("任务ID："+task.getId());
            System.out.println("流程ID："+task.getProcessInstanceId());
            System.out.println("流程定义ID："+task.getProcessDefinitionId());
            System.out.println("执行实例ID："+task.getExecutionId());
            System.out.println("任务名称："+task.getName());
            System.out.println("任务办理人："+task.getAssignee());
        }
    }

    @Test
    public void completeTask(){
        // 根据任务Id完成任务
        String taskId = "5002";
        taskService.complete(taskId);
        System.out.println("完成任务");
    }
}
