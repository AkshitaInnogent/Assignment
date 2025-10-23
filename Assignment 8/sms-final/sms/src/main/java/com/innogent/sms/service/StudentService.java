package com.innogent.sms.service;

import com.innogent.sms.dtos.RequestStudentDTO;
import com.innogent.sms.dtos.ResponseStudentDTO;
import com.innogent.sms.entities.Course;
import com.innogent.sms.entities.Student;
import com.innogent.sms.mappers.StudentMapper;
import com.innogent.sms.repositories.CourseRepository;
import com.innogent.sms.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository ;
    private final CourseRepository courseRepository;
    private final StudentMapper mapper;

    public ResponseStudentDTO createStudent(RequestStudentDTO requestStudentDTO) {
        ResponseStudentDTO responseStudentDTO = new ResponseStudentDTO();
        Student newStudent = new Student();
        newStudent.setName(requestStudentDTO.getName());
        newStudent.setPhone(requestStudentDTO.getPhone());
        newStudent.setEmail(requestStudentDTO.getEmail());
        newStudent.setCity(requestStudentDTO.getCity());
        if(requestStudentDTO.getCourseIds() != null &&  !requestStudentDTO.getCourseIds().isEmpty()) {
            for(Integer courseId : requestStudentDTO.getCourseIds()){
               Course course =  courseRepository.findById(courseId).orElse(null);
               newStudent.getCourses().add(course);
               course.getStudents().add(newStudent); //for course table
            }
        }
        studentRepository.save(newStudent);

        return mapper.convertEntityToResponseDTO(newStudent);
    }

    public void deleteStudent(int id){
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        s.getCourses().forEach(c -> c.getStudents().remove(s));
        s.getCourses().clear();
        studentRepository.delete(s);

    }

    public ResponseStudentDTO getStudent(int id) {
        Student getStudent = studentRepository.getStudentById(id);
        return mapper.convertEntityToResponseDTO(getStudent);
    }

    public ResponseStudentDTO updateStudent(int id, RequestStudentDTO requestStudentDTO){
        Student updateStudent = studentRepository.getStudentById(id);

        if(requestStudentDTO.getName()!=null)
            updateStudent.setName(requestStudentDTO.getName());

        if(requestStudentDTO.getEmail()!=null)
            updateStudent.setEmail(requestStudentDTO.getEmail());

        if(requestStudentDTO.getCity()!=null)
            updateStudent.setCity(requestStudentDTO.getCity());

        if (requestStudentDTO.getPhone() != null)
            updateStudent.setPhone(requestStudentDTO.getPhone());

        if(requestStudentDTO.getCourseIds()!=null){
            for(Course c : new HashSet<>(updateStudent.getCourses())){
                c.getStudents().remove(updateStudent); // remove old courses
            }
            updateStudent.getCourses().clear();

            for(Integer courseId : requestStudentDTO.getCourseIds()){
                Course course = courseRepository.findById(courseId).orElse(null);
                updateStudent.getCourses().add(course);
                course.getStudents().add(updateStudent);  //for course table
            }
        }

        studentRepository.save(updateStudent);

        return mapper.convertEntityToResponseDTO(updateStudent);
    }

    public List<ResponseStudentDTO> getAllStudents(){
        List<ResponseStudentDTO> responseStudentDTOList = new ArrayList<ResponseStudentDTO>();
        List<Student>studentList = studentRepository.getAllStudents();
        for (Student student : studentList) {
            responseStudentDTOList.add(mapper.convertEntityToResponseDTO(student));
        }
        return responseStudentDTOList;
    }

    public Set<ResponseStudentDTO> getStudentsByCourseName(String courseName){
        Set<Student> StudentList = studentRepository.findStudentsByCourseName(courseName);
        Set<ResponseStudentDTO> responseStudentDTOSet = new HashSet<>();
        for(Student student : StudentList){
            ResponseStudentDTO responseStudentDTO = mapper.convertEntityToResponseDTO(student);
            responseStudentDTOSet.add(responseStudentDTO);
        }
        return responseStudentDTOSet;
    }
    public List<ResponseStudentDTO> getStudentsByCityAndInstructor(String city, String instructorName){
        List<Student> studentList = studentRepository.findStudentsByCityAndInstructor(city,instructorName);
        List<ResponseStudentDTO> responseStudentDTOList = new ArrayList<>();
        for(Student student : studentList){
            ResponseStudentDTO responseStudentDTO = mapper.convertEntityToResponseDTO(student);
            responseStudentDTOList.add(responseStudentDTO);
        }
        return responseStudentDTOList;
    }

    public List<ResponseStudentDTO>getStudentsWithoutCourses(){
        List <Student> studentList = studentRepository.getStudentsWithoutCourses();
        List<ResponseStudentDTO> responseStudentDTOList = new ArrayList<>();
        for( Student student : studentList)
        {
            ResponseStudentDTO responseStudentDTO = mapper.convertEntityToResponseDTO(student);
            responseStudentDTOList.add(responseStudentDTO);
        }
        return responseStudentDTOList;
    }

}
