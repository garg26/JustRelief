package com.justreliefdoctors.models.response;

/**
 * Created by kartikeya-pc on 6/22/17.
 */

public class GetDocumentType {

    private String QualName;
    private String CouncilNumber;
    private String CouncilName;

    public String getQualName() {
        return QualName;
    }

    public void setQualName(String qualName) {
        QualName = qualName;
    }

    public String getCouncilName() {
        return CouncilName;
    }

    public void setCouncilName(String councilName) {
        CouncilName = councilName;
    }

    public String getCouncilNumber() {
        return CouncilNumber;
    }

    public void setCouncilNumber(String councilNumber) {
        CouncilNumber = councilNumber;
    }
}
