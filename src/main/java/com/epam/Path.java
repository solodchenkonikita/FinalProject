package com.epam;

/**
 * Path holder to jsp pages.
 *
 * @author Nikita Solodchenko
 */
public class Path {
    public static final String WELCOME_PAGE = "index.jsp";

    public static final String ERROR_PAGE = "/jsp/errorPage.jsp";

    public static final String PRICE_LIST_PAGE = "/jsp/priceList.jsp";

    public static final String TIMETABLE_PAGE = "/jsp/timetable.jsp";

    public static final String CLIENT_PAGE = "/jsp/clientPage.jsp";
    public static final String CLIENT_PAGE_FORWARD = "controller?command=clientPage";

    public static final String MASTER_PAGE = "/jsp/masterPage.jsp";
    public static final String MASTER_PAGE_FORWARD = "controller?command=masterPage";

    public static final String ADMINISTRATOR_PAGE = "/jsp/administratorPage.jsp";
    public static final String ADMINISTRATOR_PAGE_FORWARD = "controller?command=administratorPage";

}
