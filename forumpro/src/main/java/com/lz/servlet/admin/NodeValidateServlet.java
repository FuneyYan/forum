package com.lz.servlet.admin;

import com.lz.dto.JsonResult;
import com.lz.entity.Node;
import com.lz.service.NodeService;
import com.lz.servlet.BasicServlet;
import com.lz.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/31.
 */
@WebServlet("/admin/nodeValidate")
public class NodeValidateServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodename=req.getParameter("nodename");
        nodename=StringUtils.isoToUtf8(nodename);
        NodeService nodeService=new NodeService();
        try{
            nodeService.findNodeByNodeName(nodename);
            renderTxt("true",resp);
        }catch (RuntimeException e){
            renderTxt("false",resp);
        }

    }
}
