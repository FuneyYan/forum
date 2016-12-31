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
}
