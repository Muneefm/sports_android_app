
package com.mnf.sports.Models.GroupSearchModels;

import java.util.ArrayList;
import java.util.List;

public class GroupSearch {

    private List<Result> result = new ArrayList<Result>();
    private String status;
    private Integer total_pages;
    private Integer current_page;

    /**
     * 
     * @return
     *     The result
     */
    public List<Result> getResult() {
        return result;
    }

    /**
     * 
     * @param result
     *     The result
     */
    public void setResult(List<Result> result) {
        this.result = result;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The total_pages
     */
    public Integer getTotalPages() {
        return total_pages;
    }

    /**
     * 
     * @param total_pages
     *     The total_pages
     */
    public void setTotalPages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    /**
     * 
     * @return
     *     The currentPage
     */
    public Integer getCurrentPage() {
        return current_page;
    }

    /**
     * 
     * @param current_page
     *     The current_page
     */
    public void setCurrentPage(Integer current_page) {
        this.current_page = current_page;
    }

}
