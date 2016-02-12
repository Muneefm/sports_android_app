
package com.mnf.sports.Models.EventModel;


public class Result {

    private String name;
    private String code;
    private String type;
    private Integer over;
    private String specialtype;
    private Winnerone winnerone;
    private Winnertwo winnertwo;
    private Winnerthree winnerthree;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The over
     */
    public Integer getOver() {
        return over;
    }

    /**
     * 
     * @param over
     *     The over
     */
    public void setOver(Integer over) {
        this.over = over;
    }

    /**
     * 
     * @return
     *     The specialtype
     */
    public String getSpecialtype() {
        return specialtype;
    }

    /**
     * 
     * @param specialtype
     *     The specialtype
     */
    public void setSpecialtype(String specialtype) {
        this.specialtype = specialtype;
    }

    /**
     * 
     * @return
     *     The winnerone
     */
    public Winnerone getWinnerone() {
        return winnerone;
    }

    /**
     * 
     * @param winnerone
     *     The winnerone
     */
    public void setWinnerone(Winnerone winnerone) {
        this.winnerone = winnerone;
    }

    /**
     * 
     * @return
     *     The winnertwo
     */
    public Winnertwo getWinnertwo() {
        return winnertwo;
    }

    /**
     * 
     * @param winnertwo
     *     The winnertwo
     */
    public void setWinnertwo(Winnertwo winnertwo) {
        this.winnertwo = winnertwo;
    }

    /**
     * 
     * @return
     *     The winnerthree
     */
    public Winnerthree getWinnerthree() {
        return winnerthree;
    }

    /**
     * 
     * @param winnerthree
     *     The winnerthree
     */
    public void setWinnerthree(Winnerthree winnerthree) {
        this.winnerthree = winnerthree;
    }

}
