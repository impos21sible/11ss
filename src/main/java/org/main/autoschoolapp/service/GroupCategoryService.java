package org.main.autoschoolapp.service;
import java.util.List;
import org.main.autoschoolapp.model.GroupCategory;
import org.main.autoschoolapp.repository.ClassRoomDao;
import org.main.autoschoolapp.repository.GroupCategoryDao;

public class GroupCategoryService {
    private GroupCategoryDao groupCategoryDao = new GroupCategoryDao();

    public GroupCategoryService() {
    }

    public List<GroupCategory> findAll() {
        return groupCategoryDao.findAll();
    }

    public GroupCategory findOne(final long id) {
        return groupCategoryDao.findOne(id);
    }

    public void save(final GroupCategory entity)
    {
        if (entity == null)
            return;
        groupCategoryDao.save(entity);
    }

    public void update(final GroupCategory entity)
    {
        if (entity == null)
            return;
        groupCategoryDao.update(entity);
    }

    public void delete(final GroupCategory entity)
    {
        if (entity == null)
            return;
        groupCategoryDao.delete(entity);
    }

    public void deleteById(final Long id)
    {
        if (id == null)
            return;
        groupCategoryDao.deleteById(id);
    }
}
