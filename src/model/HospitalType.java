package model;

public enum HospitalType {
    MTL("Montreal", "MTL"),
    QUE("Quebec", "QUE"),
    SHE("Sherbrooke", "SHE");

    private final String _hospitalName;
    private final String _hospitalCode;
    HospitalType(final String hospitalName, final String hospitalCode) {
        _hospitalName = hospitalName;
        _hospitalCode = hospitalCode;
    }

    public String getHospitalName() {
        return _hospitalName;
    }

    public String getHospitalCode() {
        return _hospitalCode;
    }

    public static HospitalType findHospital(String hospitalCode) {
        for(HospitalType hospitalType : HospitalType.values()) {
            if (hospitalType._hospitalCode.equalsIgnoreCase(hospitalCode)) {
                return hospitalType;
            }
        }

        return null;
    }
}
