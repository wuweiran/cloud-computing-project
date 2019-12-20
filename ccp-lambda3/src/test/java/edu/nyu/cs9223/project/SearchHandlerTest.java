package edu.nyu.cs9223.project;

import edu.nyu.cs9223.project.search.QueryHelper;
import org.junit.Test;

import static org.junit.Assert.*;

public class SearchHandlerTest {

    @Test
    public void handleRequest() {
        QueryHelper queryHelper = new QueryHelper();
        queryHelper.searchForSpot("adel", null,
                null, null,
                4, 10,
                30);
    }
}