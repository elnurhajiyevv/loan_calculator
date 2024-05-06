package loan.calculator.domain.entity.saved

/*
 * Created by Elnur on on 05.05.24, 12.
 * Copyright (c) 2024 . All rights reserved to Elnur.
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

class ToDo(var id: Int, var name: String, var description: String, var status: String) {

    override fun toString(): String {
        return "ToDo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}'
    }
}