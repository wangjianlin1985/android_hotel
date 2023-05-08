package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.EvaluateDAO;
import com.mobileserver.domain.Evaluate;

import org.json.JSONStringer;

public class EvaluateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����������Ϣҵ������*/
	private EvaluateDAO evaluateDAO = new EvaluateDAO();

	/*Ĭ�Ϲ��캯��*/
	public EvaluateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ������Ϣ�Ĳ�����Ϣ*/
			String roomObj = "";
			if (request.getParameter("roomObj") != null)
				roomObj = request.getParameter("roomObj");
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String evaluateTime = request.getParameter("evaluateTime");
			evaluateTime = evaluateTime == null ? "" : new String(request.getParameter(
					"evaluateTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ��������Ϣ��ѯ*/
			List<Evaluate> evaluateList = evaluateDAO.QueryEvaluate(roomObj,userObj,evaluateTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Evaluates>").append("\r\n");
			for (int i = 0; i < evaluateList.size(); i++) {
				sb.append("	<Evaluate>").append("\r\n")
				.append("		<evaluateId>")
				.append(evaluateList.get(i).getEvaluateId())
				.append("</evaluateId>").append("\r\n")
				.append("		<roomObj>")
				.append(evaluateList.get(i).getRoomObj())
				.append("</roomObj>").append("\r\n")
				.append("		<userObj>")
				.append(evaluateList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<evalueScore>")
				.append(evaluateList.get(i).getEvalueScore())
				.append("</evalueScore>").append("\r\n")
				.append("		<evaluateContent>")
				.append(evaluateList.get(i).getEvaluateContent())
				.append("</evaluateContent>").append("\r\n")
				.append("		<evaluateTime>")
				.append(evaluateList.get(i).getEvaluateTime())
				.append("</evaluateTime>").append("\r\n")
				.append("	</Evaluate>").append("\r\n");
			}
			sb.append("</Evaluates>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Evaluate evaluate: evaluateList) {
				  stringer.object();
			  stringer.key("evaluateId").value(evaluate.getEvaluateId());
			  stringer.key("roomObj").value(evaluate.getRoomObj());
			  stringer.key("userObj").value(evaluate.getUserObj());
			  stringer.key("evalueScore").value(evaluate.getEvalueScore());
			  stringer.key("evaluateContent").value(evaluate.getEvaluateContent());
			  stringer.key("evaluateTime").value(evaluate.getEvaluateTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���������Ϣ����ȡ������Ϣ�������������浽�½���������Ϣ���� */ 
			Evaluate evaluate = new Evaluate();
			int evaluateId = Integer.parseInt(request.getParameter("evaluateId"));
			evaluate.setEvaluateId(evaluateId);
			String roomObj = new String(request.getParameter("roomObj").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setRoomObj(roomObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setUserObj(userObj);
			int evalueScore = Integer.parseInt(request.getParameter("evalueScore"));
			evaluate.setEvalueScore(evalueScore);
			String evaluateContent = new String(request.getParameter("evaluateContent").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setEvaluateContent(evaluateContent);
			String evaluateTime = new String(request.getParameter("evaluateTime").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setEvaluateTime(evaluateTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = evaluateDAO.AddEvaluate(evaluate);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������Ϣ����ȡ������Ϣ�ļ�¼���*/
			int evaluateId = Integer.parseInt(request.getParameter("evaluateId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = evaluateDAO.DeleteEvaluate(evaluateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����������Ϣ֮ǰ�ȸ���evaluateId��ѯĳ��������Ϣ*/
			int evaluateId = Integer.parseInt(request.getParameter("evaluateId"));
			Evaluate evaluate = evaluateDAO.GetEvaluate(evaluateId);

			// �ͻ��˲�ѯ��������Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("evaluateId").value(evaluate.getEvaluateId());
			  stringer.key("roomObj").value(evaluate.getRoomObj());
			  stringer.key("userObj").value(evaluate.getUserObj());
			  stringer.key("evalueScore").value(evaluate.getEvalueScore());
			  stringer.key("evaluateContent").value(evaluate.getEvaluateContent());
			  stringer.key("evaluateTime").value(evaluate.getEvaluateTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����������Ϣ����ȡ������Ϣ�������������浽�½���������Ϣ���� */ 
			Evaluate evaluate = new Evaluate();
			int evaluateId = Integer.parseInt(request.getParameter("evaluateId"));
			evaluate.setEvaluateId(evaluateId);
			String roomObj = new String(request.getParameter("roomObj").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setRoomObj(roomObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setUserObj(userObj);
			int evalueScore = Integer.parseInt(request.getParameter("evalueScore"));
			evaluate.setEvalueScore(evalueScore);
			String evaluateContent = new String(request.getParameter("evaluateContent").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setEvaluateContent(evaluateContent);
			String evaluateTime = new String(request.getParameter("evaluateTime").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setEvaluateTime(evaluateTime);

			/* ����ҵ���ִ�и��²��� */
			String result = evaluateDAO.UpdateEvaluate(evaluate);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
