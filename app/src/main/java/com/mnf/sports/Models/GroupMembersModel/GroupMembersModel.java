
package com.mnf.sports.Models.GroupMembersModel;

import java.util.ArrayList;
import java.util.List;

public class GroupMembersModel {

    private List<Result> result = new ArrayList<Result>();
    private String status;
    private Integer totalPages;
    private String currentPage;

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
     *     The totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     * 
     * @param totalPages
     *     The total_pages
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * 
     * @return
     *     The currentPage
     */
    public String getCurrentPage() {
        return currentPage;
    }

    /**
     * 
     * @param currentPage
     *     The current_page
     */
    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

}
