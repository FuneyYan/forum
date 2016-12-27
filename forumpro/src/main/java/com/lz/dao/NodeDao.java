package com.lz.dao;

import com.lz.entity.Node;
import com.lz.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class NodeDao {
    public List<Node> findAllNode() {
        String sql="select * from t_node";
        return DbHelp.query(sql,new BeanListHandler<Node>(Node.class));
    }

    public Node findById(Integer id) {
        String sql="select * from t_node where id=?";
        return DbHelp.query(sql,new BeanHandler<Node>(Node.class),id);
    }

    public void update(Node node) {
        String sql = "update t_node set topicnum = ?,nodename = ? where id = ?";
        DbHelp.update(sql,node.getTopicnum(),node.getNodename(),node.getId());
    }
}
