import java.util.ArrayList;
import java.util.List;

public class Perceptron {
    private List<Double> wagi = new ArrayList<>();
    private List<String[]> dataToTeach = new ArrayList<>();
    private double prog = 1;
    private final double stalaUczenia;
    public Perceptron(double stalaUczenia, List<String[]> dataToTeach2){
        for(String[] str : dataToTeach2){
            dataToTeach.add(str);
        }
        this.stalaUczenia = stalaUczenia;
        for(int i = 0; i<4; i++){
            wagi.add(1.0);
        }
    }
    public String analyze(String[] data){
        float sum = 0;
        int index = 0;
        for(String str : data){
            try {
                sum += Float.parseFloat(str.replace(",","."))*wagi.get(index);
                index++;
            }catch (Exception e){

            }
        }
        if (sum > prog){
            return "Iris-versicolor";
        }
        return "Iris-setosa";
    }
    public void teach(){
        String result;
        boolean czyJeszczeRazTeach = false;
        List<String[]> strToRemove = new ArrayList<>();
        for(String[] strArr : dataToTeach){
            float sum = 0;
            int index = 0;
            for(String str : strArr){
                try {
                    sum += Float.parseFloat(str.replace(",","."))*wagi.get(index);

                }catch (Exception e){

                }
            }
            if(sum>prog){
                result = "Iris-versicolor";
            }else{
                result = "Iris-setosa";
            }
            if (!result.equals(strArr[strArr.length-1].trim())){
                czyJeszczeRazTeach = true;
                int plusCzyMinus;
                if(sum>prog){
                    plusCzyMinus = -1;
                }
                else{
                    plusCzyMinus = 1;
                }
                List<Double> noweWagi = new ArrayList<>();
                index = 0;
                for(double i : wagi){
                    noweWagi.add(i+(plusCzyMinus*Double.parseDouble(strArr[index])*stalaUczenia));
                    index++;
                }
                wagi = noweWagi;
                prog += (plusCzyMinus*(-1)*stalaUczenia);
            }else{
                strToRemove.add(strArr);
            }
        }
        for(String[] strArr : strToRemove){
            dataToTeach.remove(strArr);
        }
        if(czyJeszczeRazTeach){
            teach();
        }
    }
}
