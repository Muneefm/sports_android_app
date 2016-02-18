
package com.mnf.sports.Models.GalleryModels;

import java.util.ArrayList;
import java.util.List;

public class GalleryModel {

    private List<Result> result = new ArrayList<Result>();
    private String status;
    private Integer total_page;
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
     *     The total_page
     */
    public Integer getTotalPage() {
        return total_page;
    }

    /**
     *
     * @param total_page
     *     The total_page
     */
    public void setTotalPage(Integer total_page) {
        this.total_page = total_page;
    }

    /**
     *
     * @return
     *     The current_page
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
