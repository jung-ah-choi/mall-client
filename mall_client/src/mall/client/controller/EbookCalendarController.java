package mall.client.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

import mall.client.model.EbookDao;


@WebServlet("/EbookCalendarController")
public class EbookCalendarController extends HttpServlet {
	private EbookDao ebookDao; // 이달의 신간 ebook을 불러오기 위해서
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.ebookDao = new EbookDao();
		
		// 년/월에 매개값으로 전달되지 않으면 
		Calendar dday = Calendar.getInstance();
		
		if(request.getParameter("currentYear") != null) {
			dday.set(Calendar.YEAR,Integer.parseInt(request.getParameter("currentYear")));
		}
		if(request.getParameter("currentMonth") != null) {
			dday.set(Calendar.MONTH, Integer.parseInt(request.getParameter("currentMonth"))-1); // 컴퓨터에서 1월은 0 값으로 시작하기 때문에 1을 빼줌
		}
		
		int currentYear = dday.get(Calendar.YEAR);
		int currentMonth = dday.get(Calendar.MONTH) + 1; // 0부터 시작하기 때문에 1을 더해줌
		
		// 월의 마지막 일을 구하는 메소드
		int endDay = dday.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		// 현재 달의 1일의 요일
		Calendar firstDay = Calendar.getInstance();
		firstDay.set(Calendar.YEAR, currentYear);
		firstDay.set(Calendar.MONTH, currentMonth-1);
		firstDay.set(Calendar.DATE, 1);
		int firstDayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK);
		
		System.out.println(currentYear+"<-- EbookCalendarController의 currentYear");
		System.out.println(currentMonth+"<-- EbookCalendarController의 currentMonth");
		System.out.println(endDay+"<-- EbookCalendarController의 endDay");
		System.out.println(firstDayOfWeek + "<-- 4/1은 목요일(5 출력)");
		
		List<Map<String, Object>> ebookListByMonth = this.ebookDao.selectEbookListByMonth(currentYear, currentMonth);
		
		// 이전달, 다음달, 연도 출력을 위한 메소드
		int preYear = currentYear;
		int preMonth = currentMonth - 1;
		if(preMonth == 0) {
			preMonth = 12;
			preYear -= 1;
		}
		
		int nextYear = currentYear;
		int nextMonth = currentMonth + 1;
		if(nextMonth == 13) {
			nextMonth = 1;
			nextYear += 1;
		}
		
		request.setAttribute("preYear", preYear);
		request.setAttribute("preMonth", preMonth);
		request.setAttribute("currentYear", currentYear);
		request.setAttribute("currentMonth", currentMonth);
		request.setAttribute("nextYear", nextYear);
		request.setAttribute("nextMonth", nextMonth);
		
		request.setAttribute("ebookListByMonth", ebookListByMonth);
		request.setAttribute("endDay", endDay);
		request.setAttribute("firstDayOfWeek", firstDayOfWeek);
		
		System.out.println(preYear);
		System.out.println(preMonth);
		System.out.println(currentYear);
		System.out.println(currentMonth);
		System.out.println(nextYear);
		System.out.println(nextMonth);
		System.out.println(ebookListByMonth);
		System.out.println(endDay);
		System.out.println(firstDayOfWeek);
		
		// view forwarding
		request.getRequestDispatcher("/WEB-INF/view/ebook/ebookCalendar.jsp").forward(request, response);
	}
}
