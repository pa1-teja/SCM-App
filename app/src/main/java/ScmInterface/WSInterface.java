package ScmInterface;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by pavan on 5/21/2015.
 */
public interface WSInterface {
    SoapObject getAllocationDetails(String dist_code,String fp_shop);
    SoapObject getStockReportDetailsInAndroid(String fp_shop);
    public SoapObject getICDSDetails(String fp_shop);
}
