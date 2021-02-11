package tz.go.moh.him.nacte.mediator.hrhis.domain;

public class HrhisRequest {
    /**
     * Academic year should reflect the end of the academic year. Example: Academic year 2019/2020 the the parameter should be 2020
     */
    int academicYear;

    /**
     * Paging number of the data fetched
     */
    int pageNumber;

    /**
     * Size of the page to be fetched
     */
    int pageSize;

    /**
     * Value of 0 will just produce the results and the value of 1 will return data including the summary
     */
    int summary;

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSummary() {
        return summary;
    }

    public void setSummary(int summary) {
        this.summary = summary;
    }
}
