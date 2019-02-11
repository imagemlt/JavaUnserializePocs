package JavaUnser;

import com.alibaba.fastjson.JSON;

import java.util.Properties;

public class babyFastjson {
    //fastjson testcase
    public String name;
    public int age;
    private Properties prop;
    private String grade;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("setter of name");
        this.name = name;
    }

    public int getAge() {
        System.out.println("getter of age");
        return age;
    }

    public void setAge(int age) {
        System.out.println("setter of age");
        this.age = age;
    }

    public Properties getProp() {
        System.out.println("getter of prop");
        if(prop==null)prop=new Properties();
        return prop;
    }

    /*public void setProp(Properties prop) {
        System.out.println("setter of prop");
        this.prop = prop;
    }*/

    public String getGrade() {
        System.out.println("getter of grade");
        return grade;
    }

    public void setGrade(String grade) {
        System.out.println("setter of grade");
        this.grade = grade;
    }

    public babyFastjson(){
        System.out.println("[+]constructor called");
    }

    public static void main(String args[]) throws Exception{
        String jsonStr="{\"@type\":\"JavaUnser.babyFastjson\",\"name\":\"Yao\",\"age\":18,\"prop\":{\"first\":\"222\"},\"grade\":\"Class 4\"}";
        babyFastjson user=JSON.parseObject(jsonStr,babyFastjson.class);
        System.exit(1);
    }
}
