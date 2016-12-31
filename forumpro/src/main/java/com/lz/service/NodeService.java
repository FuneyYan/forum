package com.lz.service;

import com.lz.dao.NodeDao;
import com.lz.entity.Node;
import com.lz.util.StringUtils;

public class NodeService {
    NodeDao nodeDao=new NodeDao();

    //    管理员删除节点
    public void delNodeById(String nodeid) {
        if(StringUtils.isNumeric(nodeid)){
            Node node=nodeDao.findById(Integer.valueOf(nodeid));
            if(node!=null){
                if(node.getTopicnum()==0){
                    nodeDao.delNodeById(nodeid);
                }else{
                    throw new RuntimeException("该节点下还有主题,暂无法删除");
                }
            }else{
                throw new RuntimeException("节点不存在");
            }
        }else{
            throw new RuntimeException("节点不存在");
        }
    }


    public String findNodeByNodeName(String nodeid,String nodename) {
        Node node=null;
        if(StringUtils.isNumeric(nodeid)){//有id,修改节点
            node=nodeDao.findById(Integer.valueOf(nodeid));
            if(node==null){
                return "false";
            }else{
                if(node.getNodename().equals(nodename)){
                    return "true";
                }else{
                    return findNodeWithName(nodename);
                }

            }
        }else{//没有id,新增节点
            return findNodeWithName(nodename);
        }

    }

    public String findNodeWithName(String nodename){
        Node node=null;
        node=nodeDao.findByNodename(nodename);
        if (node != null) {
            return "false";
        }else{
            return "true";
        }
    }

    public void addNode(String nodename) {
        nodeDao.addNode(nodename);
    }

    public Node findNodeById(String nodeid) {
        Node node=null;
        if(StringUtils.isNumeric(nodeid)){
            node=nodeDao.findById(Integer.valueOf(nodeid));
            if(node!=null){
                return node;
            }else{
                throw new RuntimeException("该节点不存在");
            }
        }else{
            throw new RuntimeException("nodiid不合法");
        }
    }

    public void updateNode(String nodeid, String nodename) {
        if(StringUtils.isNumeric(nodeid)){
            Node node=nodeDao.findById(Integer.valueOf(nodeid));
            if(node!=null){
                node.setNodename(nodename);
                nodeDao.update(node);
            }else{
                throw new RuntimeException("该节点不存在");
            }
        }else{
            throw new RuntimeException("nodiid不合法");
        }
    }
}
