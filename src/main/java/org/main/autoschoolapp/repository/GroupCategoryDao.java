package org.main.autoschoolapp.repository;

import org.main.autoschoolapp.model.GroupCategory;
import org.main.autoschoolapp.model.Instructor;

public class GroupCategoryDao extends BaseDao<GroupCategory> {
    public GroupCategoryDao() {
        super(GroupCategory.class);
    }

}
