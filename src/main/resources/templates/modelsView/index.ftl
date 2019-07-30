<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<title>Activiti6流程设计器Demo</title>
<style>
	.box {
		margin: auto;
		width: 1000px;
		border: 2px black solid;
		padding: 10px;
		margin-top: 50px;
	}
</style>
</head>
<body>
<h2>
	<a href='/model/create?name=activiti&key=123456'>绘制流程模型</a>
</h2>
<div class="box">
	<h4>流程模型</h4>
	<table width="100%">
	    <tr>
	        <td>模型编号</td>
	        <td>版本</td>
	        <td>模型名称</td>
	        <td>模型key</td>
	        <td>操作</td>
	    </tr>
	        <#list modelList as model>
	        <tr>
	            <td>${model.id}</td>
	            <td>${model.version}</td>
	            <td><#if (model.name)??>${model.name}<#else> </#if></td>
	            <td><#if (model.key)??>${model.key}<#else> </#if></td>
	            <td>
	             <a href="/model/editor?modelId=${model.id}">编辑</a>
	             <a href="/model/publish?modelId=${model.id}">发布</a>
	             <a href="/model/deleteModel?modelId=${model.id}">删除</a>
	             <a href="/model/createdProcessImage?modelId=${model.id}">生成流程图片</a>
	            </td>
	        </tr>
	       </#list>
	</table>
</div>
<div class="box">
	<h4>流程列表</h4>
	<table width="100%">
		<tr>
			<td>流程id</td>
			<td>流程名称</td>
			<td>流程发布时间</td>
			<td>操作</td>
		</tr>
		<#list processList as process>
			<tr>
				<td>${process.id}</td>
				<td><#if process.name ?? >${process.name}</#if></td>
				<td>${process.deploymentTime ? string('yyyy-MM-dd hh:mm:ss') }</td>
				<td>
					<a href="/editor?modelId=${process.id}">编辑</a>
					<a href="/publish?modelId=${process.id}">发布</a>
				</td>
			</tr>
		</#list>
	</table>
</div>
</body>
</html>