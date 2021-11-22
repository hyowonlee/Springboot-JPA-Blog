package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;

//이거 UserRepository랑 같음 거기서 주석보셈

public interface BoardRepository extends JpaRepository <Board, Integer> //인터페이스가 인터페이스를 상속받을땐 extends 사용 
{
	
}
