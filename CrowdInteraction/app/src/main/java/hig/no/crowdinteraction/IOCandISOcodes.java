package hig.no.crowdinteraction;

/**
 * Created by Mimoza on 11/14/2014.
 * Class to handle nationalities: country name, ioc and iso codes
 */
public class IOCandISOcodes {

    private int id;
    private String ioc, iso, coutryName;

    public IOCandISOcodes() {
    }

    public IOCandISOcodes(String ioc, String iso, String coutryName) {
        super();
        this.ioc = ioc;
        this.iso = iso;
        this.coutryName = coutryName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIoc(String ioc) {
        this.ioc = ioc;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public void setCoutryName(String coutryName) {
        this.coutryName = coutryName;
    }

    public String getIoc(){
        return ioc;
    }

    public String getIso() {
        return iso;
    }

    public String getCoutryName() {
        return coutryName;
    }

    @Override
    public String toString() {
        return coutryName + " ("+ ioc +")";
    }
}
