package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.AddressModel;
import com.example.model.UserModel;
import com.example.service.IUser;

@RestController
public class UserController {

    @Autowired
    IUser userService;

    @RequestMapping(value = "/user/model", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getModel() {
        UserModel user = new UserModel();
        AddressModel address = new AddressModel();
        List<AddressModel> addressList = new ArrayList<AddressModel>();
        addressList.add(address);
        user.setAddresses(addressList);
        return (ResponseEntity<?>) ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<?> saveUser(@RequestBody UserModel userModel) {
        UserModel user = userService.saveUser(userModel);
        return (ResponseEntity<?>) ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/update/{ID}", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> updateUser(@RequestBody UserModel userModel, @PathVariable("ID") Long id) {
        UserModel user = userService.updateUser(id, userModel);
        if (user == null) {
            return (ResponseEntity<?>) ResponseEntity.noContent();
        }
        return (ResponseEntity<?>) ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/delete/{ID}", method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity<?> deleteUser(@PathVariable("ID") Long id) {
        if (userService.deleteUser(id)) {
            return (ResponseEntity<?>) ResponseEntity.ok();
        }
        return (ResponseEntity<?>) ResponseEntity.noContent();
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getListUser() {
        List<UserModel> listUser = (List<UserModel>) userService.getList();
        return (ResponseEntity<?>) ResponseEntity.ok(listUser);
    }

    @RequestMapping(value = "/get/{ID}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getUser(@PathVariable("ID") Long id) {
        UserModel user = userService.getOne(id);
        if (user == null) {
            return (ResponseEntity<?>) ResponseEntity.noContent();
        }
        return (ResponseEntity<?>) ResponseEntity.ok(user);
    }

}
