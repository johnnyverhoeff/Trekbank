/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbank;

/**
 *
 * @author Johnny
 */
public class MeasureSubProfile {
 
    private int aantalInSerie;
    private String beschrijving;
    private String certificatNumber;
    private String proefLast;

    /**
     * Get the value of certificatNumber
     *
     * @return the value of certificatNumber
     */
    public String getCertificatNumber() {
        return certificatNumber;
    }

    /**
     * Set the value of certificatNumber
     *
     * @param certificatNumber new value of certificatNumber
     */
    public void setCertificatNumber(String certificatNumber) {
        this.certificatNumber = certificatNumber;
    }

    public int getAantalInSerie() {
        return aantalInSerie;
    }

    public void setAantalInSerie(int aantalInSerie) {
        this.aantalInSerie = aantalInSerie;
    }

    public String getProefLast() {
        return proefLast;
    }

    public void setProefLast(String proefLast) {
        this.proefLast = proefLast;
    }

    

    public MeasureSubProfile(int aantal, String beschrijving, String certificaatNumber, String proefLast) {
        this.aantalInSerie = aantal;
        this.beschrijving = beschrijving;
        this.certificatNumber = certificaatNumber;
        this.proefLast = proefLast;
    }

    public int getAantal() {
        return aantalInSerie;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    
    
    /*public static ArrayList<MeasureSubProfile> testProfile(){
        ArrayList<MeasureSubProfile> measureProfile = new ArrayList<MeasureSubProfile>();
        measureProfile.add(new MeasureSubProfile(2, "Ketting"));
        measureProfile.add(new MeasureSubProfile(1, "schakel"));
        measureProfile.add(new MeasureSubProfile(1, "boom"));
        measureProfile.add(new MeasureSubProfile(5, "Harp"));
        return measureProfile;
    }
    
    public static ArrayList<MeasureSubProfile> testProfile2(){
        ArrayList<MeasureSubProfile> measureProfile = new ArrayList<MeasureSubProfile>();
        for (int i = 0; i <= 10; i++){
            measureProfile.add(new MeasureSubProfile(i, i + "e de test"));
        }
        return measureProfile;
    }
    
    public static ArrayList<MeasureSubProfile> testProfile3(){
        ArrayList<MeasureSubProfile> measureProfile = new ArrayList<MeasureSubProfile>();
        for (int i = 0; i <= 100; i++){
            measureProfile.add(new MeasureSubProfile(i + 1, (i + 1) + "e test"));
        }
        return measureProfile;
    }
    */

    @Override
    public String toString() {
        return "MeasureSubProfile{" + "aantalInSerie=" + aantalInSerie + ", beschrijving=" + beschrijving + ", certificatNumber=" + certificatNumber + ", proefLast=" + proefLast + '}';
    }
}
