package com.project.helloworld.repository;

import com.project.helloworld.domain.Sticker;
import com.project.helloworld.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerRepository extends JpaRepository<Sticker,Long> {
}