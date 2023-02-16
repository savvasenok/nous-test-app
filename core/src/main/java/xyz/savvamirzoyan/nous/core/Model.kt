package xyz.savvamirzoyan.nous.core

sealed interface Model {

    interface Ui : Model
    interface Domain : Model
    sealed interface Data : Model {
        interface Local : Data
        interface Cloud : Data
    }
}