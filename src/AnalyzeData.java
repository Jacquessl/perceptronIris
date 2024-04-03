import java.util.*;

public class AnalyzeData {
    private  String[] dataToAnalyze;
    private List<String[]> data;
    private int k;
    public AnalyzeData(List<String[]> data, String[] dataToAnalyze, int k){
        this.data = data;
        this.dataToAnalyze = dataToAnalyze;
        this.k = k;
    }
    public String analyze(){
        if(k>data.size()){
            return "Za dużo sąsiadów";
        }
        Map<String, Set<Double>> mapa = new HashMap<>();
        double[] najblizsze = new double[k];
        for(int i = 0; i<najblizsze.length;i++){
            najblizsze[i] = Double.MAX_VALUE;
        }
        for(String[] str : data){
            mapa.putIfAbsent(str[str.length-1], new TreeSet<>());
            double odleglosc = Math.pow((Float.parseFloat(str[0].replace(",",".")) - Float.parseFloat(dataToAnalyze[0].replace(",","."))), 2) +
                    Math.pow((Float.parseFloat(str[1].replace(",",".")) - Float.parseFloat(dataToAnalyze[1].replace(",","."))), 2) +
                    Math.pow((Float.parseFloat(str[2].replace(",",".")) - Float.parseFloat(dataToAnalyze[2].replace(",","."))), 2) +
                    Math.pow((Float.parseFloat(str[3].replace(",",".")) - Float.parseFloat(dataToAnalyze[3].replace(",","."))), 2);
            mapa.get(str[str.length-1]).add(odleglosc);
        }
        Map<String, List<Double>> result = new HashMap<>();
        for(Map.Entry<String, Set<Double>> entrySet : mapa.entrySet()){
            int index = 0;
            for(double wartosc : entrySet.getValue()){
                if(najblizsze[index]>=wartosc){
                    result.putIfAbsent(entrySet.getKey(), new ArrayList<>());
                    result.get(entrySet.getKey()).add(wartosc);
                    if(najblizsze[index]>wartosc){
                        for(Map.Entry<String, Set<Double>> entrySet2 : mapa.entrySet()){
                            if(entrySet2.getValue().contains(najblizsze[index])){
                                result.get(entrySet2.getKey()).remove(najblizsze[index]);
                            }
                        }
                    }
                    najblizsze[index] = wartosc;
                    index++;
                }else{
                    break;
                }
                if(index==najblizsze.length){
                    break;
                }
            }
        }
        int[] typy = new int[3];
        int index=0;
        String[] strResult = new String[3];
        for(Map.Entry<String, List<Double>> entrySet : result.entrySet()){
            if(typy[index]<entrySet.getValue().size()){
                typy[index] = entrySet.getValue().size();
                strResult[index] = entrySet.getKey();
            }
            if(typy[index]==entrySet.getValue().size() && typy[index]!=0){
                index++;
                typy[index] = entrySet.getValue().size();
                strResult[index] = entrySet.getKey();
            }
        }
        for(int i = 0;i<typy.length-1;i++){
            for(int j = i+1;j<typy.length;j++){
                if(typy[i]<typy[j]){
                    String tmpStr = strResult[i];
                    strResult[i] = strResult[j];
                    strResult[j] = tmpStr;
                    int tmp = typy[i];
                    typy[i] = typy[j];
                    typy[j] = tmp;
                }
            }
        }
        for(int i = typy.length-1; i>0; i--){
            if (typy[i]!=0 && typy[i]==typy[i-1]){
                if(i==typy.length-1) {
                    if (typy[0] == typy[i]) {
                        return strResult[(int) (Math.random() * (i + 1))];
                    }
                }
                else{
                        return strResult[(int) (Math.random() * (i + 1))];
                    }
            }
        }
        return strResult[0];
    }
}
