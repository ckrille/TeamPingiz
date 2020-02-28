package com.example.demo.forum;

import com.example.demo.user.User;
import com.example.demo.user.UserController;
import com.example.demo.user.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
public class ForumController {

    @Autowired
    ForumRepository forumRepository;

    @Autowired
    ForumService forumService;



    @GetMapping("/forum")
    public String showForum(HttpSession session, @RequestParam(required = false, defaultValue = "0") Integer page, Model model){
        session.setAttribute("postsAtt", forumRepository.getForumPostList());
        model.addAttribute("page",page);
        model.addAttribute("currentPage",page);
        model.addAttribute("show",forumRepository.splitIntoPages(page));
        return "forum";
    }

    @GetMapping("/forum/{page}")
    public String showForum1(HttpSession session, @RequestParam(required = false, defaultValue = "1") Integer page, Model model){
        session.setAttribute("postsAtt", forumRepository.getForumPostList());
        model.addAttribute("page",page);
        model.addAttribute("currentPage",page);
        model.addAttribute("show",forumRepository.splitIntoPages(2));
        return "forum";
    }




    @PostMapping("/forum")
    public String postToForum(Model model,@RequestParam(required = false, defaultValue = "") String inputText, HttpSession session,@RequestParam(required = false, defaultValue = "1") Integer page){

        //String currentUser = (String)session.getAttribute("currentUser");

        forumService.addPost(session,inputText);
        forumService.sortPosts();

       /* forumRepository.forumPostList.add(new ForumPost(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM - yyyy")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),inputText,
                currentUser));*/

     /*   forumRepository.forumPostList.sort(Comparator.comparingInt(ForumPost::getPostNum));
        Collections.reverse(forumRepository.forumPostList);*/

        session.setAttribute("postsAtt",forumRepository.getForumPostList());


        model.addAttribute("page",page);
        model.addAttribute("currentPage",page);

        return "forum";
    }
}
