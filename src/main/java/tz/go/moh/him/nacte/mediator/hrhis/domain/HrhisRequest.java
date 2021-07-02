package tz.go.moh.him.nacte.mediator.hrhis.domain;

public class HrhisRequest {
    /**
     * Value of 0 will just produce the results and the value of 1 will return data including the summary
     */
    SummaryType summary;

    /**
     * Academic year should reflect the end of the academic year. Example: Academic year 2019/2020 the the parameter should be 2020
     */
    private int academicYear;

    /**
     * Paging number of the data fetched
     */
    private int pageNumber;

    /**
     * The endpoint queried
     */
    private String endpoint;

    /**
     * Size of the page to be fetched
     */
    private int pageSize;

    /**
     * The institution Code
     */
    private String institutionCode;

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

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public int getSummary() {
        return summary.getValue();
    }

    public void setSummary(SummaryType summary) {
        this.summary = summary;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public enum SummaryType {
        RESULTS(0), SUMMARY(1);
        private final int value;

        SummaryType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
