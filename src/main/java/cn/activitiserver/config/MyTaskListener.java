package cn.activitiserver.config;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 任务监听器，当用户节点有任务监听器时触发，设置指定用户
 */
public class MyTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        // 设置任务办理人
        delegateTask.setAssignee("黄飞鸿");
    }
}
