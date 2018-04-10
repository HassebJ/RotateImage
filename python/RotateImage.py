#!/usr/bin/env python

import math as math


class AngleCorrection(object):

    def get_x_cordinate_of_white_pixel(self, line):
        temp = line.find("1")
        if temp < 0:
            return temp
        return temp / 2

    def find_correction_angle(self, img):
        # don't want to read the CSV header
        lines_read = 1
        first_white_pixel_x = 0
        first_white_pixel_y = 1

        # find the first white pixel denoting the start of one of the sides of the rectangle
        while lines_read < len(img):
            current_row = img[lines_read]
            temp = self.get_x_cordinate_of_white_pixel(current_row)
            if temp > -1:
                first_white_pixel_x = temp
                first_white_pixel_y = lines_read
                break
            lines_read += 1

        last_white_pixel_x = first_white_pixel_x
        last_white_pixel_y = first_white_pixel_y
        is_clockwise = False

        # find which direction is the square tilted, if at all
        while lines_read < len(img):
            next_line = img[lines_read]
            current_white_pixel_x = self.get_x_cordinate_of_white_pixel(next_line)
            lines_read += 1
            if current_white_pixel_x != first_white_pixel_x:
                is_clockwise = current_white_pixel_x < first_white_pixel_x
                last_white_pixel_x = current_white_pixel_x
                last_white_pixel_y = lines_read
                break

        # find the last pixel representing the other end of the one of the sides of the rectangle
        while lines_read < len(img):
            line = img[lines_read]
            temp = self.get_x_cordinate_of_white_pixel(line)
            if is_clockwise:
                if temp > last_white_pixel_x:
                    break
            else:
                if temp < last_white_pixel_x:
                    break
            last_white_pixel_x = temp
            lines_read += 1
            last_white_pixel_y = lines_read

        y = last_white_pixel_y - first_white_pixel_y
        x = last_white_pixel_x - first_white_pixel_x
        return int(math.degrees(math.atan(y/float(x))))

if __name__ == "__main__":

    with open("rotated.csv") as f:
        rotated_img = f.readlines()
    content = [x.strip() for x in rotated_img]

    angle = AngleCorrection().find_correction_angle(img=rotated_img)
    print(angle)
