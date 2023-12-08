package com.example.demo.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entities.Image;
import com.example.demo.Exceptions.ImageException;
import com.example.demo.Repo.ImageRepo;

@Service
public class ImageService {
    @Autowired
    private ImageRepo ir;

    private void validate(String name, String mime, byte[] content) throws ImageException {
        if (name == null || name.isEmpty()) {
            throw new ImageException("The image has no name");
        }
        if (mime == null || mime.isEmpty()) {
            throw new ImageException("The image has no type or mime");
        }
        if (content == null || content.length == 0) {
            throw new ImageException("The image has no content");
        }
    }

    @Transactional
    public Image create(String name, String mime, byte[] content) throws ImageException {
        validate(name, mime, content);

        Image image = new Image();

        image.setName(name);
        image.setMime(mime);
        image.setContent(content);

        return ir.save(image);
    }

    @Transactional(readOnly = true)
    public Image getId(String id) throws ImageException {
        Optional<Image> res = ir.findById(id);

        if (res.isPresent()) {
            return res.get();
        }

        throw new ImageException("The image was not found");
    }
}
