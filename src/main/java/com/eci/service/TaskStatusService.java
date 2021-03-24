package com.eci.service;

import java.io.IOException;
import java.sql.SQLException;

import com.eci.bean.Task;
import com.eci.bean.TaskStatus;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface TaskStatusService {

	ObjectNode getTaskStatusList(int userid);

	ObjectNode findPoListByNo(String poNum);

	ObjectNode assignPo(Task task);

	ObjectNode saveReopenJob(Task task);

	ObjectNode getTaskFrmAction(int userid);

}
