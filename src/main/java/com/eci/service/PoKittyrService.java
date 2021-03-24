package com.eci.service;

import com.eci.bean.PoKitty;

import com.fasterxml.jackson.databind.node.ObjectNode;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface PoKittyrService {


	ObjectNode getPOKittyList(int userid);

	ObjectNode updatePOremark(PoKitty poKitty);

	
	
	
	
}
