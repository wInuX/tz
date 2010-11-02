package tz.game.service;

import com.google.inject.ImplementedBy;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(CopyServiceImpl.class)
public interface CopyService {
    <T> T copy(T source);
}
