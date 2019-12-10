package com.haohua.socket;

import com.haohua.socket.model.Student;
import com.haohua.socket.model.Teacher;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * collection  demo
 */
@SpringBootTest
class SocketApplicationTests {

    /**
     * 修改list中某个对象的属性 并转换为map
     */
    @Test
    void listToMap() {
        Map<String, Student> map = new HashMap<>();
        Student student = new Student();
        student.setAge("1");
        student.setHobby("睡觉");
        student.setName("tom");
        student.setRegion("巩义市");
        student.setSex("男");

        Student student1 = new Student();
        student1.setAge("10");
        student1.setHobby("看电影");
        student1.setName("rose");
        student1.setRegion("郑州市");
        student1.setSex("女");
        ArrayList<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student1);
        map = list.stream().map(a -> {
            a.setName(a.getName().toUpperCase());
            return a;
        }).collect(Collectors.toMap(item -> item.getName(), item -> item));
        System.out.println(map);
    }

    /**
     * 将map转换为list
     */
    @Test
    void mapToList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("rose", "yuwen");
        map.put("jack", "math");
        map.put("alex", "wuli");
        List<Teacher> collect = map.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey())).map(e -> new Teacher(e.getValue(), e.getKey())).collect(Collectors.toList());
        for (Teacher teacher : collect) {
            System.out.println(teacher);
        }
    }


    /**
     * 从list 中过滤一个元素
     */
    @Test
    void filterList() {
        Student student = new Student();
        student.setAge("1");
        student.setHobby("睡觉");
        student.setName("tom");
        student.setRegion("巩义市");
        student.setSex("男");

        Student student1 = new Student();
        student1.setAge("10");
        student1.setHobby("看电影");
        student1.setName("rose");
        student1.setRegion("郑州市");
        student1.setSex("女");
        List<Student> list = Lists.list(student, student1);
        List<Student> collect = list.stream().filter(e -> "女".equals(e.getSex())).collect(Collectors.toList());
        for (Student student2 : collect) {
            System.out.println(student2);
        }

    }


    /**
     * 单个list 具有相同属性值的 去重复合并
     * 统计年纪相同学生的个数
     * 条件：将年纪和性别相同的学生其他属性合并显示，返回一个集合.
     */
    @Test
    void removeDuplicate() {
        Student student = new Student();
        student.setAge("12");
        student.setHobby("睡觉");
        student.setName("tom");
        student.setRegion("巩义市");
        student.setSex("男");

        Student student1 = new Student();
        student1.setAge("12");
        student1.setHobby("看书");
        student1.setName("june");
        student1.setRegion("焦作市");
        student1.setSex("男");

        Student student2 = new Student();
        student2.setAge("10");
        student2.setHobby("看电影");
        student2.setName("rose");
        student2.setRegion("郑州市");
        student2.setSex("女");

        Student student3 = new Student();
        student3.setAge("12");
        student3.setHobby("看韩剧");
        student3.setName("alex");
        student3.setRegion("许昌市");
        student3.setSex("女");

        List<Student> list = Lists.list(student, student1, student2, student3);
        // 统计年纪相同的个数
        Map<String, Long> collect = list.stream().collect(Collectors.groupingBy(s -> s.getSex(), Collectors.counting()));
        System.out.println(collect);
        // 将年纪和性别相同的对象分组 其他属性合并显示
        Map<String, Map<String, List<Student>>> collect1 = list.stream().collect(Collectors.groupingBy(Student::getSex, Collectors.groupingBy(Student::getAge)));
        ArrayList<Student> list1 = new ArrayList<>();
        for (Map.Entry<String, Map<String, List<Student>>> entry : collect1.entrySet()) {
            Map<String, List<Student>> value = entry.getValue();
            for (Map.Entry<String, List<Student>> entry1 : value.entrySet()) {
                Student student4 = new Student();
                student4.setSex(entry.getKey());
                String nameTotal = "";
                String hobbyTotal = "";
                String regionTotal = "";
                student4.setAge(entry1.getKey());
                for (Student student5 : entry1.getValue()) {
                    nameTotal += student5.getName();
                    hobbyTotal += student5.getHobby();
                    regionTotal += student5.getRegion();
                }
                student4.setName(nameTotal);
                student4.setRegion(regionTotal);
                student4.setHobby(hobbyTotal);
                list1.add(student4);
            }
        }
        for (Student s : list1) {
            System.out.println(s);
        }
        // 或者使用org.apache.commons.lang3.tuple中的pair类
        ArrayList<Student> list2 = new ArrayList<>();
        Map<Pair<String, String>, List<Student>> collect2 = list.stream().collect(Collectors.groupingBy(student42 -> Pair.of(student42.getSex(), student42.getAge())));
        for (Map.Entry<Pair<String, String>, List<Student>> entry : collect2.entrySet()) {
            Student student4 = new Student();
            student4.setAge(entry.getKey().getRight());
            student4.setSex(entry.getKey().getLeft());
            String nameTotal = "";
            String hobbyTotal = "";
            String regionTotal = "";
            for (Student student5 : entry.getValue()) {
                nameTotal += student5.getName();
                hobbyTotal += student5.getHobby();
                regionTotal += student5.getRegion();
            }
            student4.setName(nameTotal);
            student4.setRegion(regionTotal);
            student4.setHobby(hobbyTotal);
            list2.add(student4);
        }
        for (Student s : list2) {
            System.out.println(s);
        }
    }


    /**
     * 将俩个list 合并
     * 条件：两个list中对象中 某个属性值相同的 其他属性相加去重复然后合并
     */
    @Test
    void combineList() {
        Teacher teacher = new Teacher("huaxue", "rose");
        Teacher teacher1 = new Teacher("math", "jack");
        Teacher teacher2 = new Teacher("huaxue", "kl");
        List<Teacher> list = Lists.list(teacher, teacher1, teacher2);

        Teacher teacher3 = new Teacher("wuli", "rose");
        Teacher teacher4 = new Teacher("shengwu", "oa");
        List<Teacher> list1 = Lists.list(teacher3, teacher4);
        // 根据姓名属性去重复,并且合并属性值
        list.addAll(list1);
        Map<String, Teacher> collect = list.stream().collect(Collectors.toMap(Teacher::getName, Function.identity(), (e, f) -> {
            f.setSubject(e.getSubject() + f.getSubject());
            return f;
        }));
        ArrayList<Teacher> values = new ArrayList<>(collect.values());
        values.forEach(e -> System.out.println(e.getName() + "--" + e.getSubject()));

    }


}
