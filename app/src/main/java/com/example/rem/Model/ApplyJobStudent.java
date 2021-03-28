package com.example.rem.Model;

public class ApplyJobStudent {
    String companyname, companydescription,jobpost,workingtype,name,email,qualification,studentfield, status,uid;

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanydescription() {
        return companydescription;
    }

    public void setCompanydescription(String companydescription) {
        this.companydescription = companydescription;
    }

    public String getJobpost() {
        return jobpost;
    }

    public void setJobpost(String jobpost) {
        this.jobpost = jobpost;
    }

    public String getWorkingtype() {
        return workingtype;
    }

    public void setWorkingtype(String workingtype) {
        this.workingtype = workingtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getStudentfield() {
        return studentfield;
    }

    public void setStudentfield(String studentfield) {
        this.studentfield = studentfield;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ApplyJobStudent(String companyname, String companydescription, String jobpost, String workingtype, String name, String email, String qualification, String studentfield, String status,String uid) {
        this.companyname = companyname;
        this.companydescription = companydescription;
        this.jobpost = jobpost;
        this.workingtype = workingtype;
        this.name = name;
        this.email = email;
        this.qualification = qualification;
        this.studentfield = studentfield;
        this.status=status;
        this.uid=uid;
    }
}
