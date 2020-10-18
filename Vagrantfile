# -*- mode: ruby -*-
# vi: set ft=ruby :


#Configuring virtuale machine1
Vagrant.configure("2") do |config|
	
   config.vm.define "one", primary: true do |one|
    #Making this machine primary among all the Vm
    #Naming virtual machine 
    one.vm.box = "ubuntu/trusty64" 
    #selecting vagrant box
    one.vm.network "private_network", ip: "192.168.33.10",
   #Giving private network ip 
   virtualbox__intnet: true
   #Allows communications between different Vm's
    one.vm.hostname = "one"
    #Definig the Vm host name
    one.vm.synced_folder ".", "/vagrant", type: "nfs"
    #Sync with .vagrant file from its location
    one.vm.provider "virtualbox" do |vb|
      #Customise the VM
      vb.customize ["modifyvm", :id, "--name", "one"]
      vb.gui = true
      #Enable gui 
     #Allocate memory
    vb.memory ="1024"
    end

    
    #DEclartion of provision and runnig shell commands for installation
    one.vm.provision "shell",  inline:  <<-SHELL

   add-apt-repository ppa:openjdk-r/ppa -y
   apt-get update
   sudo apt-get update
   $ sudo apt update
   $ sudo apt install software-properties-common  
   $ sudo apt-add-repository --yes --update ppa:ansible/ansible
   $ sudo apt install ansible



   SHELL

  end


   

config.vm.define "two" do |two|
#Defining VM two and rest all thing are same
 
   two.vm.box = "ubuntu/trusty64"
    two.vm.network "private_network", ip: "192.168.33.11",
    virtualbox__intnet: true
    two.vm.hostname = "two"
    two.vm.synced_folder ".", "/vagrant", type: "nfs"
    two.vm.provider "virtualbox" do |vb|
     vb.customize ["modifyvm", :id, "--name", "two"]
      vb.memory ="1024"
    end
 end 
end
