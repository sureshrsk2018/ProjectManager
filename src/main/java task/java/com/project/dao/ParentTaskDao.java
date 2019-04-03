package com.project.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.ParentTask;

@Repository
public interface ParentTaskDao<P> extends CrudRepository<ParentTask,Long> {
	public List<ParentTask> findByParentTaskId(long parentTaskId);
}
