package com.eci.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.eci.bean.Customer;
import com.eci.bean.Region;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface RegionDao {
	
	ObjectNode getRegions();
	
	ObjectNode getRegionDetail(int id);
	
	ObjectNode insertRegion(Region region) throws ClassNotFoundException, IOException, SQLException;
	
	ObjectNode updateRegion(Region region);
	
	ObjectNode inactivateRegion(Region region);

	ObjectNode activateRegion(Region region);
	
}
