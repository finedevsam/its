package com.yorksj.itsproject.utils;

import com.yorksj.itsproject.entity.*;
import com.yorksj.itsproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private SubjectsRepository subjectsRepository;

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private QuestionsBankRepository questionsBankRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Create Admin User at Code Initialization

        List<Subjects> checkSubject = subjectsRepository.findAll();
        if(checkSubject.isEmpty()){
//            Subject 1 and Topic one
            List<Map<String, Object>> humanTopics = new ArrayList<>();

            Map<String, Object> humanTopics1 = new HashMap<>();
            // Cultural Geography Questions
            List<Map<String, Object>> culturalGeographyQuestionsList = new ArrayList<>();
            Map<String, Object> culturalGeographyQuestions1 = new HashMap<>();
            culturalGeographyQuestions1.put("question", "What does cultural geography examine?");
            culturalGeographyQuestions1.put("option1", "1. Spatial distribution of cultures");
            culturalGeographyQuestions1.put("option2", "2. Chemical reactions");
            culturalGeographyQuestions1.put("option3", "3. Genetic mutations");

            Map<String, Object> culturalGeographyQuestions2 = new HashMap<>();
            culturalGeographyQuestions2.put("question", "Which is an aspect of cultural geography?");
            culturalGeographyQuestions2.put("option1", "1. Language diversity");
            culturalGeographyQuestions2.put("option2", "2. Particle physics");
            culturalGeographyQuestions2.put("option3", "3. Automotive engineering");
            culturalGeographyQuestionsList.add(culturalGeographyQuestions1);
            culturalGeographyQuestionsList.add(culturalGeographyQuestions2);
            humanTopics1.put("question", culturalGeographyQuestionsList);
            humanTopics1.put("title", "Cultural Geography");
            humanTopics1.put("content", "Cultural geography examines the spatial distribution of cultural traits, behaviors, and ideas, exploring how they shape and are shaped by the physical environment, human interactions, and societal patterns.");

            Map<String, Object> humanTopics2 = new HashMap<>();
            // Economic Geography Questions
            List<Map<String, Object>> economicGeographyQuestionsList = new ArrayList<>();
            Map<String, Object> economicGeographyQuestions1 = new HashMap<>();
            economicGeographyQuestions1.put("question","What does economic geography study?");
            economicGeographyQuestions1.put("option1", "1. Distribution of economic activities");
            economicGeographyQuestions1.put("option2", "2. Weather patterns");
            economicGeographyQuestions1.put("option3", "3. Historical events");

            Map<String, Object> economicGeographyQuestions2 = new HashMap<>();
            economicGeographyQuestions2.put("question","Which is a key focus of economic geography?");
            economicGeographyQuestions2.put("option1", "1. Global trade routes");
            economicGeographyQuestions2.put("option2", "2. Musical genres");
            economicGeographyQuestions2.put("option3", "3. Artistic movements");

            economicGeographyQuestionsList.add(economicGeographyQuestions1);
            economicGeographyQuestionsList.add(economicGeographyQuestions2);
            
            humanTopics2.put("title", "Economic Geography");
            humanTopics2.put("content", "Economic Geography studies spatial distribution of economic activities, resources, and development, exploring how geography influences production, trade, and regional disparities in wealth and well-being.");
            humanTopics2.put("question", economicGeographyQuestionsList);

            Map<String, Object> humanTopics3 = new HashMap<>();
            // Population Questions
            List<Map<String, Object>> populationQuestionsList = new ArrayList<>();
            Map<String, Object> populationQuestions1 = new HashMap<>();

            populationQuestions1.put("question", "What is population in geography?");
            populationQuestions1.put("option1", "1. Total number of individuals in an area");
            populationQuestions1.put("option2", "2. Land area");
            populationQuestions1.put("option3", "3. Temperature");

            Map<String, Object> populationQuestions2 = new HashMap<>();

            populationQuestions2.put("question", "Which factor affects population distribution?");
            populationQuestions2.put("option1", "1. Climate");
            populationQuestions2.put("option2", "2. Number of shopping malls");
            populationQuestions2.put("option3", "3. Fictional literature");
            populationQuestionsList.add(populationQuestions1);
            populationQuestionsList.add(populationQuestions2);
            
            humanTopics3.put("title", "Population");
            humanTopics3.put("content", "Population refers to the total number of individuals, whether people, animals, or plants, living in a specific area or environment at a given time, influencing demographics and resource distribution.");
            humanTopics3.put("question", populationQuestionsList);

            Map<String, Object> humanTopics4 = new HashMap<>();
            
            // Urbanization Questions
            List<Map<String, Object>> urbanizationQuestionsList = new ArrayList<>();
            Map<String, Object> urbanizationQuestions1 = new HashMap<>();
            urbanizationQuestions1.put("question", "What is urbanization?");
            urbanizationQuestions1.put("option1", "1. Process of rural-to-urban migration");
            urbanizationQuestions1.put("option2", "2. Agricultural practice");
            urbanizationQuestions1.put("option3", "3. Geological transformation");

            Map<String, Object> urbanizationQuestions2 = new HashMap<>();
            urbanizationQuestions2.put("question", "Which factor is NOT typically associated with urbanization?");
            urbanizationQuestions2.put("option1", "1. Population concentration");
            urbanizationQuestions2.put("option2", "2. Infrastructure development");
            urbanizationQuestions2.put("option3", "3. Crop cultivation");

            urbanizationQuestionsList.add(urbanizationQuestions1);
            urbanizationQuestionsList.add(urbanizationQuestions2);
            humanTopics4.put("title", "Urbanization");
            humanTopics4.put("content", "Urbanization is the process of population migration from rural to urban areas, leading to increased concentration of people in cities. It involves social, economic, and infrastructural changes in urban settings.");
            humanTopics4.put("question", urbanizationQuestionsList);

            humanTopics.add(humanTopics1);
            humanTopics.add(humanTopics2);
            humanTopics.add(humanTopics3);
            humanTopics.add(humanTopics4);

            Subjects subject1 = new Subjects();
            subject1.setName("Human Geography");

            subjectsRepository.save(subject1);

            for(Map<String, Object> obj: humanTopics){
                Topics topics = new Topics();
                topics.setTitle(obj.get("title").toString());
                topics.setContent(obj.get("content").toString());
                topics.setSubjects(subject1);
                topicsRepository.save(topics);

                List<Map<String, Object>> question = (List<Map<String, Object>>) obj.get("question");
                for(Map<String, Object> i: question){
                    QuestionsBank questionsBank = new QuestionsBank();
                    questionsBank.setQuestion(i.get("question").toString());
                    questionsBank.setAnswer("1");
                    questionsBank.setOptionOne(i.get("option1").toString());
                    questionsBank.setOptionTwo(i.get("option2").toString());
                    questionsBank.setOptionThree(i.get("option3").toString());
                    questionsBank.setTopics(topics);

                    questionsBankRepository.save(questionsBank);
                }

            }

            // For Subject 2 and Topic 2

            List<Map<String, Object>> physicalTopics = new ArrayList<>();

            Map<String, Object> physicalTopic1 = new HashMap<>();
            
            // Geology Questions
            List<Map<String, Object>> geologyQuestionsList = new ArrayList<>();
            Map<String, Object> geologyQuestions1 = new HashMap<>();
            geologyQuestions1.put("question", "What does geology study?");
            geologyQuestions1.put("option1", "1. Earth's structure and composition");
            geologyQuestions1.put("option2", "2. Human languages");
            geologyQuestions1.put("option3", "3. Political systems");

            Map<String, Object> geologyQuestions2 = new HashMap<>();
            geologyQuestions2.put("question", "Which is a branch of geology?");
            geologyQuestions2.put("option1", "1. Seismology");
            geologyQuestions2.put("option2", "2. Astrophysics");
            geologyQuestions2.put("option3", "3. Computer programming");

            geologyQuestionsList.add(geologyQuestions1);
            geologyQuestionsList.add(geologyQuestions2);
            
            physicalTopic1.put("title", "Geology");
            physicalTopic1.put("content", "Geology is the scientific study of the Earth's structure, composition, and processes, including the study of rocks, minerals, and the forces shaping the planet's surface over time.");
            physicalTopic1.put("question", geologyQuestionsList);

            Map<String, Object> physicalTopic2 = new HashMap<>();
            // Landforms Questions
            List<Map<String, Object>> landformsQuestionsList = new ArrayList<>();
            Map<String, Object> landformsQuestions1 = new HashMap<>();
            landformsQuestions1.put("question", "What are landforms?");
            landformsQuestions1.put("option1", "1. Natural features of the Earth's surface");
            landformsQuestions1.put("option2", "2. Man-made structures");
            landformsQuestions1.put("option3", "3. Cultural practices");

            Map<String, Object> landformsQuestions2 = new HashMap<>();
            landformsQuestions2.put("question", "Which is an example of a landform?");
            landformsQuestions2.put("option1", "1. Mountain");
            landformsQuestions2.put("option2", "2. Skyscraper");
            landformsQuestions2.put("option3", "3. Shopping mall");
            landformsQuestionsList.add(landformsQuestions1);
            landformsQuestionsList.add(landformsQuestions2);
            
            physicalTopic2.put("title", "Landforms");
            physicalTopic2.put("content", "Landforms are natural features of the Earth's surface, including mountains, valleys, plains, plateaus, and more. They are shaped by geological processes like erosion, weathering, and tectonic activity.");
            physicalTopic2.put("question", landformsQuestionsList);

            Map<String, Object> physicalTopic3 = new HashMap<>();
            // Meteorology Questions
            List<Map<String, Object>> meteorologyQuestionsList = new ArrayList<>();
            Map<String, Object> meteorologyQuestions1 = new HashMap<>();
            meteorologyQuestions1.put("question", "What is meteorology?");
            meteorologyQuestions1.put("option1", "1. Study of the Earth's atmosphere and weather");
            meteorologyQuestions1.put("option2", "2. Study of fossils");
            meteorologyQuestions1.put("option3", "3. Study of ancient civilizations");

            Map<String, Object> meteorologyQuestions2 = new HashMap<>();
            meteorologyQuestions2.put("question", "Which instrument is used in meteorology?");
            meteorologyQuestions2.put("option1", "1. Barometer");
            meteorologyQuestions2.put("option2", "2. Microscope");
            meteorologyQuestions2.put("option3", "3. Telescope");

            meteorologyQuestionsList.add(meteorologyQuestions1);
            meteorologyQuestionsList.add(meteorologyQuestions2);
            physicalTopic3.put("title", "Meteorology");
            physicalTopic3.put("content", "Meteorology is the scientific study of the Earth's atmosphere, focusing on weather patterns, climate, and phenomena. Meteorologists analyze atmospheric conditions to forecast and understand atmospheric processes.");
            physicalTopic3.put("question", meteorologyQuestionsList);

            Map<String, Object> physicalTopic4 = new HashMap<>();
            // Ecosystem Questions
            List<Map<String, Object>> ecosystemQuestionsList = new ArrayList<>();
            Map<String, Object> ecosystemQuestions1 = new HashMap<>();
            ecosystemQuestions1.put("question", "What is an ecosystem?");
            ecosystemQuestions1.put("option1", "1. Biological community and its physical environment");
            ecosystemQuestions1.put("option2", "2. Financial system");
            ecosystemQuestions1.put("option3", "3. Political ideology");

            Map<String, Object> ecosystemQuestions2 = new HashMap<>();
            ecosystemQuestions2.put("question", "Which is a component of an ecosystem?");
            ecosystemQuestions2.put("option1", "1. Plants");
            ecosystemQuestions2.put("option2", "2. Stock market");
            ecosystemQuestions2.put("option3", "3. Fashion trends");

            ecosystemQuestionsList.add(ecosystemQuestions1);
            ecosystemQuestionsList.add(ecosystemQuestions2);
            physicalTopic4.put("question", ecosystemQuestionsList);
            physicalTopic4.put("title", "Ecosystem");
            physicalTopic4.put("content", "An ecosystem is a community of living organisms interacting with their physical environment. It includes plants, animals, microorganisms, and their habitat, functioning as a self-sustaining, interconnected system.");

            physicalTopics.add(physicalTopic1);
            physicalTopics.add(physicalTopic2);
            physicalTopics.add(physicalTopic3);
            physicalTopics.add(physicalTopic4);


            Subjects subject2 = new Subjects();
            subject2.setName("Physical Geography");
            subjectsRepository.save(subject2);

            for(Map<String, Object> obj: physicalTopics){
                Topics topics = new Topics();
                topics.setTitle(obj.get("title").toString());
                topics.setContent(obj.get("content").toString());
                topics.setSubjects(subject2);
                topicsRepository.save(topics);

                List<Map<String, Object>> question = (List<Map<String, Object>>) obj.get("question");

                for(Map<String, Object> i: question){
                    QuestionsBank questionsBank = new QuestionsBank();
                    questionsBank.setQuestion(i.get("question").toString());
                    questionsBank.setAnswer("1");
                    questionsBank.setOptionOne(i.get("option1").toString());
                    questionsBank.setOptionTwo(i.get("option2").toString());
                    questionsBank.setOptionThree(i.get("option3").toString());
                    questionsBank.setTopics(topics);

                    questionsBankRepository.save(questionsBank);
                }
            }
        }

        List<Map<String, Object>> teachers = getMaps();

        for(Map<String, Object> i: teachers){
            if(! userRepository.existsByEmail(i.get("email").toString())){
                String userId = null;
                User user = new User();
                user.setEmail(i.get("email").toString());
                user.setPassword(passwordEncoder.encode(i.get("password").toString()));
                user.setIsTeacher(true);
                userId = String.format("T-%s", userId());

                Profile profile = new Profile();
                profile.setFirstName(i.get("firstName").toString());
                profile.setLastName(i.get("lastName").toString());
                profile.setUser(user);
                profile.setIdNo(userId);

                userRepository.save(user);
                profileRepository.save(profile);
            }
        }
    }

    private static List<Map<String, Object>> getMaps() {
        List<Map<String, Object>> teachers = new ArrayList<>();

        Map<String, Object> teacher1 = new HashMap<>();
        teacher1.put("firstName", "Samson");
        teacher1.put("lastName", "Ilemobayo");
        teacher1.put("email", "samson.i@gmail.com");
        teacher1.put("password", "123456");


        Map<String, Object> teacher2 = new HashMap<>();
        teacher2.put("firstName", "Rikome");
        teacher2.put("lastName", "Erezi");
        teacher2.put("email", "rikome.e@gmail.com");
        teacher2.put("password", "123456");

        teachers.add(teacher2);
        teachers.add(teacher1);
        return teachers;
    }

    private static String userId() {
        Random random = new Random();
        int maxValue = (int) Math.pow(10, 7);
        int randomNumber = random.nextInt(maxValue);
        return String.valueOf(randomNumber);
    }

}
