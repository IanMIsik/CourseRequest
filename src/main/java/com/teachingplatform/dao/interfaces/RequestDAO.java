package com.teachingplatform.dao.interfaces;

import com.teachingplatform.model.CourseRequest;

public interface RequestDAO {

    void insertRequest(CourseRequest request) throws DAOException;
    void updateRequestStatus(int requestId, String status) throws DAOException;


}
