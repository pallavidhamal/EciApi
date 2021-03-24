package com.eci.dao;

import com.eci.bean.PoKitty;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface PoKittyDao {
	
	

	ObjectNode getPOKittyList(int userid);

	ObjectNode updatePOremark(PoKitty poKitty);

	
}

