package com.eci.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.eci.bean.Installation;
import com.eci.bean.Task;
import com.eci.bean.TaskStatus;
import com.eci.bean.User;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface TaskStatusDao {

	ObjectNode getTaskStatusList(int userid);

	ObjectNode findPoListByNo(String poNum);

	ObjectNode assignPo(Task task);

	ObjectNode saveReopenJob(Task task);

	ObjectNode getTaskFrmAction(int userid);

	
}
