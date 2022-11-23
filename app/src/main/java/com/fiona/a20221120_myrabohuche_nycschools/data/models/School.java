package com.fiona.a20221120_myrabohuche_nycschools.data.models;

public class School {
    private String dbn;
    private String school_name;

    public School(String dbn, String school_name) {
        this.dbn = dbn;
        this.school_name = school_name;
    }

    public String getDbn() {
        return dbn;
    }

    public void setDbn(String dbn) {
        this.dbn = dbn;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }
}
