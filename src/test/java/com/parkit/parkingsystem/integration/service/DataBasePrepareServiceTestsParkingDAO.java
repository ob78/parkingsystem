package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataBasePrepareServiceTestsParkingDAO {
	
    DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	//DataBaseConfig dataBaseTestConfig = new DataBaseConfig();
	
    public void updateParkingSpotDAOTest_SetParkingOneAvailabilityToTrue(){
        Connection connection = null;
        try{
            connection = dataBaseTestConfig.getConnection();

            //set entry for parking 1 available = TRUE
            PreparedStatement ps = connection.prepareStatement("update parking set available = true where PARKING_NUMBER = 1");
            ps.execute();
            
            dataBaseTestConfig.closePreparedStatement(ps);
            
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }
    
    public void updateParkingSpotDAOTest_SetParkingOneAvailabilityToFalse(){
        Connection connection = null;
        try{
            connection = dataBaseTestConfig.getConnection();

            //set entry for parking 1 available = FALSE
            PreparedStatement ps = connection.prepareStatement("update parking set available = false where PARKING_NUMBER = 1");
            ps.execute();

            dataBaseTestConfig.closePreparedStatement(ps);
            
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }

    public boolean getParkingSpotDAOTest_GetAvailabilityParkingOne(){
        Connection connection = null;
        boolean result=false;
        try{
            connection = dataBaseTestConfig.getConnection();

            PreparedStatement ps = connection.prepareStatement("select AVAILABLE from parking where PARKING_NUMBER = 1");
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                result = rs.getBoolean(1);
            }

            dataBaseTestConfig.closePreparedStatement(ps);
            dataBaseTestConfig.closeResultSet(rs);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);
        }
        return result;
    }
    
    public void updateParkingSpotDAOTest_SetAllToTrue(){
        Connection connection = null;
        try{
            connection = dataBaseTestConfig.getConnection();

            //set entry for all parking available = TRUE
            PreparedStatement ps = connection.prepareStatement("update parking set available = true");
            ps.execute();
                        
            dataBaseTestConfig.closePreparedStatement(ps);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }
    
    public void updateParkingSpotDAOTest_SetAllToFalse(){
        Connection connection = null;
        try{
            connection = dataBaseTestConfig.getConnection();

            //set entry for all parking available = FALSE
            PreparedStatement ps = connection.prepareStatement("update parking set available = false");
            ps.execute();

            dataBaseTestConfig.closePreparedStatement(ps);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }
    
    public void clearDataBaseEntries(){
        Connection connection = null;
        try{
            connection = dataBaseTestConfig.getConnection();

            //set parking entries to available
            connection.prepareStatement("update parking set available = true").execute();

            //clear ticket entries;
            connection.prepareStatement("truncate table ticket").execute();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }
    
}
